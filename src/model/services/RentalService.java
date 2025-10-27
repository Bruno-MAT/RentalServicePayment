package model.services;

import model.entites.CarRental;
import model.entites.Invoice;

import java.time.Duration;

public class RentalService {

    private Double pricePerDay;
    private Double pricePerHour;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental){

        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes/60.0;

        double basicPayment;
        if(hours <= 12){
            basicPayment = pricePerHour * Math.ceil(hours);
        }
        else {
            double days = hours/24;
            basicPayment = pricePerDay * Math.ceil(days);
        }

        double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment,tax));


    }

}
