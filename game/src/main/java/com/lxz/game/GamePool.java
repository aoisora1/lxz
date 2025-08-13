package com.lxz.game;

import com.lxz.game.config.GamePoolProperties;
import com.lxz.game.context.StartContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GamePool {
    private final Logger logger = LoggerFactory.getLogger(GamePool.class);
    private Map<Integer, Game> gaming; // 正在进行的对局
    private List<Game> coreGame; // 核心对局
    private LinkedList<Game> gamesFree; // 非核心空闲对局
    private GamePoolProperties properties;
    private long expirationTime;
    private Lock lock;
    private GameFactory gameFactory;
    private ScheduledThreadPoolExecutor executor;

    public GamePool(GamePoolProperties properties, GameFactory gameFactory, ScheduledThreadPoolExecutor executor) {
        this.gaming = new HashMap<>();
        this.coreGame = new ArrayList<>();
        this.properties = properties;
        this.gamesFree = new LinkedList<>();
        this.expirationTime = (long) properties.getKeepAliveTime() * 60 * 1000;
        this.lock = new ReentrantLock();
        this.gameFactory = gameFactory;
        this.executor = executor;
        this.startDaemon();
    }

    public Game newGame(StartContext context) {
        Game game;
        try {
            lock.lock();
            if (gaming.size() > properties.getMaxSize()) {
                throw new RuntimeException("对局已满请稍等!");
            }

            // 先使用核心对局，先撑满核心对局数
            if (coreGame.size() < properties.getCoreSize()) {
                game = gameFactory.newGame(context);
                game.startGame(context);
                coreGame.add(game);
                gaming.put(game.getId(), game);
                return game;
            }

            // 核心对局已满，优先使用核心非进行中对局
            for (int i = 0; i < coreGame.size(); i++) {
                if (!coreGame.get(i).isGaming()) {
                    coreGame.get(i).startGame(context);
                    gaming.put(coreGame.get(i).getId(), coreGame.get(i));
                    return coreGame.get(i);
                }
            }

            // 使用非核心空闲对局
            if (!gamesFree.isEmpty()) {
                Game first = gamesFree.pop();
                first.startGame(context);
                gaming.put(first.getId(), first);
                return first;
            }

            // 新建非核心对局
            game = gameFactory.newGame(context);
            game.startGame(context);
            gaming.put(game.getId(), game);
        } finally {
            lock.unlock();
        }
        return game;
    }

    public void release(int id) {
        try {
            lock.lock();
            Game game = gaming.remove(id);
            if (!coreGame.contains(game)) {
                gamesFree.addLast(game);
            }
        } finally {
            lock.unlock();
        }
    }

    public Game get(int id) {
        return gaming.get(id);
    }

    private void startDaemon() {
        executor.scheduleWithFixedDelay(() -> {
            logger.info("开始释放非核心空闲对局");
            logger.info("对局信息：进行中对局{}，非核心空闲对局{}", gaming.size(), gamesFree.size());
            releaseFree();
            logger.info("对局信息：进行中对局{}，非核心空闲对局{}", gaming.size(), gamesFree.size());
            logger.info("结束释放非核心空闲对局");
        }, 1L, 1L, TimeUnit.MINUTES);
    }

    private void releaseFree() {
        try {
            lock.lock();
            long currentTime = System.currentTimeMillis();
            Iterator<Game> iterator = gamesFree.iterator();
            while (iterator.hasNext()) {
                Game next = iterator.next();
                if (currentTime - next.getStartTime() > this.expirationTime) {
                    logger.error("对局{}超时释放", next.getId());
                    iterator.remove();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public String getGameInfo() {
        StringBuilder info = new StringBuilder();
        info.append("进行中对局:");
        info.append(gaming.size());
        info.append(".核心对局:");
        info.append(coreGame.size());
        info.append(".非核心空闲对局:");
        info.append(gamesFree.size());
        return info.toString();
    }

}
