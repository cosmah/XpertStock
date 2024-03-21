package com.inventoryxpert.application.views.Invoicing.invoice;

import com.inventoryxpert.application.backend.entity.Person;
import com.inventoryxpert.application.backend.service.PersonService;
import com.inventoryxpert.application.views.MainLayout;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "CreateInvoice", layout = MainLayout.class)
public class CreateInvoice extends Div {

    private static Grid<Person> grid;
    private static Div hint;

    private final PersonService personService;

    @Autowired
    public CreateInvoice(PersonService personService) {
        this.personService = personService;
        setupTextBoxes();

    }

    private void setupTextBoxes() {
        // Create a horizontal layout
        HorizontalLayout rowLayout = new HorizontalLayout();
    
        HorizontalLayout cellGroupLayout = new HorizontalLayout();
    
        TextField productField = new TextField("Product");
        TextField descriptionField = new TextField("Description");
        TextField quantityField = new TextField("Quantity");
        TextField costField = new TextField("Cost");
        TextField totalCostField = new TextField("Total Cost");
    
        cellGroupLayout.add(productField, descriptionField, quantityField, costField, totalCostField);
    
        // Create a plus icon button
        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        plusButton.getStyle().set("width", "24px").set("height", "24px");
    
        // Create a delete icon button
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.getStyle().set("width", "24px").set("height", "24px");
    
        rowLayout.add(plusButton, cellGroupLayout, deleteButton);
    
        // Add the row layout to the view
        add(rowLayout);
    }

}
