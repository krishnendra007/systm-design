import java.time.Duration;

class HourlyPricingStrategy implements PricingStrategy {
    @Override
    public double calculate(Ticket ticket) {
        long hours = Duration.between(
                ticket.getEntryTime(),
                ticket.getExitTime()
        ).toHours();

        return Math.max(10, hours * 20); // base price
    }
}