package com.github.limecode.tchallenges.event.impl;

import com.github.limecode.tchallenges.utils.ChallengeTimer;
import com.github.limecode.tchallenges.event.Event;

public class TimerStartEvent extends Event {
    private ChallengeTimer timer;

    public TimerStartEvent(ChallengeTimer timer){
        this.timer = timer;
    }

    public ChallengeTimer getTimer() {
        return timer;
    }
}
