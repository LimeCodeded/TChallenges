package com.github.limecode.tchallenges.event.impl;

import com.github.limecode.tchallenges.utils.ChallengeTimer;
import com.github.limecode.tchallenges.event.Event;

public class TimerResumeEvent extends Event {
    private ChallengeTimer timer;

    public TimerResumeEvent(ChallengeTimer timer){
        this.timer = timer;
    }

    public ChallengeTimer getTimer() {
        return timer;
    }
}
