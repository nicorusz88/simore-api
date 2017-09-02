package ar.com.simore.simoreapi.exceptions;

public class TreatmentTemplateNotFoundException extends Throwable {

    public TreatmentTemplateNotFoundException(String templateId) {
        super(String.format("Treatment Template with ID %s was not found", templateId));
    }
}
