package com.inventoryxpert.application.views.Invoicing.invoice;

import com.inventoryxpert.application.backend.entity.Person;
import com.inventoryxpert.application.backend.service.PersonService;
import com.inventoryxpert.application.views.MainLayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "CreateInvoice", layout = MainLayout.class)
public class CreateInvoice extends Div {

    private static Div hint;

    private final PersonService personService;
    private Grid<Person> grid; // Define the Grid component
    private ListDataProvider<Person> dataProvider;

    private TextField productField;
    private TextField descriptionField;
    private TextField quantityField;
    private TextField costField;
    private TextField totalCostField;

    @Autowired
    public CreateInvoice(PersonService personService) {
        this.personService = personService;
        setupTextBoxes(); // Ensure this method is called to set up the UI
    }

    private void setupTextBoxes() {
        // Create a horizontal layout
        HorizontalLayout rowLayout = new HorizontalLayout();

        HorizontalLayout cellGroupLayout = new HorizontalLayout();

        productField = new TextField("Product");
        descriptionField = new TextField("Description");
        quantityField = new TextField("Quantity");
        costField = new TextField("Cost");
        totalCostField = new TextField("Total Cost");

        cellGroupLayout.add(productField, descriptionField, quantityField, costField, totalCostField);

        // Initialize the Grid component
        grid = new Grid<>();

        grid.addColumn(Person::getProduct).setHeader("Product");
        grid.addColumn(Person::getDescription).setHeader("Description");
        grid.addColumn(Person::getQuantity).setHeader("Quantity");
        grid.addColumn(Person::getCost).setHeader("Cost");
        grid.addColumn(Person::getTotalCost).setHeader("Total Cost");

        dataProvider = DataProvider.ofCollection(new ArrayList<>());
        grid.setDataProvider(dataProvider);

        // Create a plus icon button
        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        plusButton.getStyle().set("width", "24px").set("height", "24px");
        plusButton.addClickListener(event -> {
            // Example: Add a new Person object to the grid
            // You'll need to replace this with actual logic to create a new Person object
            Person newPerson = new Person(); // Example, replace with actual logic
            dataProvider.getItems().add(newPerson);
            dataProvider.refreshAll(); // Refresh the grid to show the new item
        });

        // Create a delete icon button
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.getStyle().set("width", "24px").set("height", "24px");
        deleteButton.addClickListener(event -> {
            if (!dataProvider.getItems().isEmpty()) {
                dataProvider.getItems().remove(dataProvider.getItems().size() - 1);
                dataProvider.refreshAll(); // Refresh the grid to show the new item
            }
        });

        rowLayout.add(plusButton, cellGroupLayout, deleteButton);

        // Add the row layout to the view
        add(rowLayout);

        add(grid);
    }

    private void addNewPerson() {
        Person newPerson = new Person();
        newPerson.setProduct(productField.getValue());
        newPerson.setDescription(descriptionField.getValue());
        newPerson.setQuantity(Integer.parseInt(quantityField.getValue()));
        newPerson.setCost(Double.parseDouble(costField.getValue()));
        newPerson.setTotalCost(newPerson.getQuantity() * newPerson.getCost());

        dataProvider.getItems().add(newPerson);
        dataProvider.refreshAll(); // Refresh the grid to show the new item
    }
}
