package benchmark.actor.threadpool;

import actor.Message;
import benchmark.actor.ActorBenchmarkConfig;
import benchmark.actor.parallel.AbstractDispatcher;
import benchmark.actor.parallel.Mailbox;

/**
 * A runnable for actor scheduling. When executed, the actor consumes a certain maximum amount of
 * messages from its mailbox. After the execution session the actor is unscheduled. If there are
 * further messages in its mailbox, the actor is scheduled for execution again.
 * 
 * @author patrick.peschlow
 */
public class ActorRunnable implements Runnable {

    private final AbstractDispatcher dispatcher;

    private final Mailbox mailbox;

    public ActorRunnable(AbstractDispatcher dispatcher, Mailbox mailbox) {
	this.dispatcher = dispatcher;
	this.mailbox = mailbox;
    }

    @Override
    public void run() {
	consumeMessages();
	mailbox.setScheduled(false);
	if (!mailbox.isEmpty()) {
	    dispatcher.scheduleUnlessAlreadyScheduled(mailbox.getActor().getId());
	}
    }

    private void consumeMessages() {
	int counter = 0;
	Message message;
	while (counter++ < ActorBenchmarkConfig.MAX_CONSUME_BURST
		&& (message = mailbox.pollMessage()) != null) {
	    mailbox.getActor().receive(message);
	}
    }
}
