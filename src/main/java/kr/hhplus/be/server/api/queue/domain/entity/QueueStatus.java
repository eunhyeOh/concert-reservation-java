package kr.hhplus.be.server.api.queue.domain.entity;

public enum QueueStatus {
    WAITING,    //대기
    PROGRESS,   //진행(유효)
    DONE,       //완료
    EXPIRED     //만료
}
