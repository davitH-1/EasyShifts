@Service
public class ShiftAutomationService {
    private final OllamaChatModel chatModel;
    private final CalendarService calendarService; // Your GCal implementation
    private final MapsService mapsService;         // Your Google Maps implementation

    public ShiftAutomationService(OllamaChatModel chatModel, CalendarService cs, MapsService ms) {
        this.chatModel = chatModel;
        this.calendarService = cs;
        this.mapsService = ms;
    }

    public void processSchedule(String pdfText) {
        // Gemma 4 native JSON extraction
        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ValetShift>>() {});

        String promptText = """
            Extract all available shifts from this valet schedule. 
            Return only a JSON array. Use date format YYYY-MM-DD.
            Schedule: %s
            """.formatted(pdfText);

        List<ValetShift> shifts = outputConverter.convert(chatModel.call(promptText));

        for (ValetShift shift : shifts) {
            if (isShiftWorthIt(shift)) {
                sendClaimEmail(shift);
            }
        }
    }

    private boolean isShiftWorthIt(ValetShift shift) {
        boolean hasConflict = calendarService.hasConflict(shift.date(), shift.startTime());
        int travelTime = mapsService.getTravelTime(shift.location());

        return !hasConflict && travelTime <= 45;
    }
}