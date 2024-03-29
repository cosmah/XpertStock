package com.inventoryxpert.application.views.products;

import com.inventoryxpert.application.backend.entity.Product;
import com.inventoryxpert.application.backend.service.ProductService;
import com.inventoryxpert.application.views.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.html.Anchor;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@PageTitle("Products")
@CssImport("./themes/inventoryxpert/views/inventory.css")
@Route(value = "inventory", layout = MainLayout.class)
public class Inventory extends VerticalLayout {

    private Grid<Product> grid = new Grid<>(Product.class);
    private final ProductService productService;
    private TextField filterText = new TextField();
    private ActionsForm form;

    @Autowired
    public Inventory(ProductService productService) {
        this.productService = productService;

        addClassNames("listView");
        setSizeFull();

        configureFilter();
        configureGrid();

        // Create the ActionsForm instance
        form = new ActionsForm(productService.findAll());

        // Add event listeners to the ActionsForm
        form.addListener(ActionsForm.SaveEvent.class, this::saveProduct);
        form.addListener(ActionsForm.DeleteEvent.class, this::deleteProduct);
        form.addListener(ActionsForm.CloseEvent.class, e -> closeEditor());

        // Fetch data from ProductService and set it in the grid
        populateGrid();

        // Create the download button
        Anchor downloadButton = createDownloadButton();

        // Create a layout for the filter text field and the download button
        HorizontalLayout topLayout = new HorizontalLayout(filterText, downloadButton);
        topLayout.setWidthFull();
        topLayout.setJustifyContentMode(JustifyContentMode.END);

        // Add the form and grid to the layout
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        // Add the top layout and the content to the layout
        add(topLayout, content);

        updateList();
        closeEditor();
    }

    private void deleteProduct(ActionsForm.DeleteEvent event) {
        productService.delete(event.getProduct());
        updateList();
        closeEditor();

    }

    // saveContact method
    private void saveProduct(ActionsForm.SaveEvent event) {
        productService.save(event.getProduct());
        updateList();
        closeEditor();
    }

    private void updateList() {
        List<Product> products = productService.findAll(filterText.getValue());
        grid.setItems(products);
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter products");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        grid.addClassName("product-grid");
        grid.setSizeFull();

        grid.setColumns("productCode", "productName", "quantity",
                "price", "resalePrice", "supplier");

        grid.asSingleSelect().addValueChangeListener(event -> editProduct(event.getValue()));
    }

    private void editProduct(Product product) {
        if (product == null) {
            closeEditor();
        } else {
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");

        }
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void populateGrid() {
        // Fetch the list of products from the ProductService
        // Replace findAll() with your method to retrieve products
        // Ensure your ProductService fetches data correctly from the repository
        List<Product> productList = productService.findAll();

        // Set the fetched data in the grid
        grid.setItems(productList);

        // configure size
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    // Step 2: Create method to generate Excel file
    public ByteArrayInputStream productsToExcel(List<Product> products) throws IOException {
        String[] columns = { "ID", "Product Name", "Product Code", "Product Description", "Price", "Quantity",
                "Starting Date", "Supplier", "Resale Price" };
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Products");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIdx = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getProductName());
                row.createCell(2).setCellValue(product.getProductCode());
                row.createCell(3).setCellValue(product.getProductDescription());
                row.createCell(4).setCellValue(product.getPrice());
                row.createCell(5).setCellValue(product.getQuantity());
                row.createCell(6).setCellValue(product.getStartingDate().toString());
                row.createCell(7).setCellValue(product.getSupplier());
                row.createCell(8).setCellValue(product.getResalePrice());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private Anchor createDownloadButton() {
        StreamResource resource = new StreamResource("products.xlsx", () -> {
            try {
                return productsToExcel(productService.findAll());
            } catch (IOException e) {
                e.printStackTrace();
                return new ByteArrayInputStream(new byte[0]);
            }
        });
        Anchor downloadButton = new Anchor(resource, "Download");
        downloadButton.getElement().setAttribute("download", true);
        return downloadButton;
    }

}
