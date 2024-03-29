package com.inventoryxpert.application.views.people.employees;

import java.util.List;

import com.inventoryxpert.application.backend.entity.Employee;
import com.inventoryxpert.application.backend.service.EmployeeService;
import com.inventoryxpert.application.views.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Employees")
@CssImport("./themes/inventoryxpert/views/inventory.css")
@Route(value = "employees", layout = MainLayout.class)
public class Employees extends VerticalLayout{

    private Grid<Employee> grid = new Grid<>(Employee.class);
    private final EmployeeService employeeService; 
    private TextField filterText = new TextField();
    private ActionsForm form;

    public Employees(EmployeeService employeeService) {
        this.employeeService = employeeService;

        addClassNames("listView");
        setSizeFull();

        configureGrid();
        configureFilter();

        form = new ActionsForm(employeeService.findAll());
        form.addListener(ActionsForm.SaveEvent.class, this::saveEmployee);
        form.addListener(ActionsForm.DeleteEvent.class, this::deleteEmployee);
        form.addListener(ActionsForm.CloseEvent.class, e -> closeEditor());

 
        
        populateGrid();

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);

        updateList();
        closeEditor();
    }


    private void deleteEmployee(ActionsForm.DeleteEvent event) {
        employeeService.delete(event.getEmployee());
        updateList();
        closeEditor();
    }	

    private void saveEmployee(ActionsForm.SaveEvent evt) {
        employeeService.save(evt.getEmployee());
        updateList();
        closeEditor();
    }



    private void populateGrid() {
        List<Employee> employees = employeeService.findAll();
        grid.setItems(employees);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void closeEditor() {
        form.setEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by employeeName...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        List<Employee> employees = employeeService.findAll(filterText.getValue());
        grid.setItems(employees);
    }

    private void configureGrid() {
        grid.addClassName("product-grid");
        grid.setSizeFull();
        grid.setColumns("employeeName", "employeeAddress", "employeePhone", "employeeNextOfKin",  "employeeDateOfBirth", "employeeGender", "employeeOccupation", "employeeMaritalStatus");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> editEmployee(evt.getValue()));
    }

    private void editEmployee(Employee employee) {
        if (employee == null) {
            closeEditor();
        } else {
            form.setEmployee(employee);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    
}