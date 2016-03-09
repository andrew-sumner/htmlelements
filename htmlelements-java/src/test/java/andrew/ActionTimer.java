package andrew;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ActionTimer {
	final ZonedDateTime startwait;
	
	private ActionTimer() {
		this.startwait = now();
	}

	public static ActionTimer start() {
		return new ActionTimer();
	}
	
	public Duration duration() {
		return Duration.between(startwait, now());
	}
	
	private static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of("NZ"));
    }
}
