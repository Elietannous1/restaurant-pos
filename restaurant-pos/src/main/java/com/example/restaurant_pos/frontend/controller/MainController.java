package com.example.restaurant_pos.frontend.controller;

import com.example.restaurant_pos.frontend.controller.utils.TokenManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private ComboBox<String> cbTimeFilter;

    @FXML
    private BarChart<String, Number> barChart;

    // Make sure the type here is VBox since our FXML uses a VBox for the bottom container.
    @FXML
    private VBox barChartContainer;

    private String token = TokenManager.getInstance().getToken();

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Store product IDs and names dynamically
    private final Map<Integer, String> productNames = new HashMap<>();

    @FXML
    public void initialize() {
        // Setup ComboBox
        cbTimeFilter.getItems().clear();
        cbTimeFilter.getItems().addAll("Daily", "Weekly", "Monthly");
        cbTimeFilter.setValue("Daily");

        // Fetch products from the API and update the chart
        fetchProductNames();

        // Update chart when ComboBox selection changes
        cbTimeFilter.setOnAction(event -> updateChart(cbTimeFilter.getValue().toLowerCase()));
    }

    private void fetchProductNames() {
        String apiUrl = "http://localhost:8080/product"; // API for fetching products
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Products: " + response.body());
            List<ProductData> products = objectMapper.readValue(response.body(), new TypeReference<>() {});
            productNames.clear();
            for (ProductData product : products) {
                productNames.put(product.id(), product.productName());
            }
            // Update chart with default "daily" period
            updateChart("daily");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChart(String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        switch (period) {
            case "weekly":
                startDate = endDate.minusDays(7);
                break;
            case "monthly":
                startDate = endDate.minusDays(30);
                break;
            default:
                startDate = endDate;
        }

        String startStr = startDate.format(DateTimeFormatter.ISO_DATE);
        String endStr = endDate.format(DateTimeFormatter.ISO_DATE);

        String apiUrl = "http://localhost:8080/sales/top-selling?startDate=" + startStr + "&endDate=" + endStr;

        try {
            if (token == null) {
                System.out.println("No token found! Please log in.");
                return;
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Top-selling response: " + response.body());
            List<SalesData> salesDataList = objectMapper.readValue(response.body(), new TypeReference<>() {});

            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(period.substring(0, 1).toUpperCase() + period.substring(1) + " Sales");

            for (SalesData data : salesDataList) {
                String productName = productNames.getOrDefault(data.product(), "Unknown Product");
                series.getData().add(new XYChart.Data<>(productName, data.quantitySold()));
            }
            barChart.getData().add(series);

            // Only request layout update if barChartContainer is not null
            if (barChartContainer != null) {
                barChartContainer.requestLayout();
            } else {
                System.out.println("barChartContainer is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public record SalesData(int id, int product, String saleDate, int quantitySold) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductData(int id, String productName) {}

    @FXML
    private void loadDashboard() {
        loadView("/com/restaurantpos/views/dashboard-view.fxml");
    }

    @FXML
    private void loadOrders() {
        loadView("/com/restaurantpos/views/orders-view.fxml");
    }

    @FXML
    private void loadMenuManagement() {
        loadView("/com/restaurantpos/views/menu-management-view.fxml");
    }

    @FXML
    private void loadSettings() {
        loadView("/com/restaurantpos/views/settings-view.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            System.out.println("Error loading view: " + e.getMessage());
        }
    }
}
