package com.example.proyectovideojuegosydescuentos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase que representa un objeto de tipo `Juego`.
 * Contiene la información que he querido coger de la API sobre los juegos.
 */
public class Juego implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("normalPrice")
    private String normalPrice;
    @SerializedName("salePrice")
    private String salePrice;
    @SerializedName("thumb")
    private String thumb;
    @SerializedName("savings")
    private String savings;
    @SerializedName("steamRatingPercent")
    private String steamRatingPercent;
    @SerializedName("dealLink")
    private String dealLink;

    //constructor vacío
    public Juego() {
    }

    /**
     * Constructor de la clase Juego.
     *
     * @param title El título del juego.
     * @param normalPrice El precio original del juego.
     * @param salePrice El precio con el descuento aplicado.
     * @param thumb La imagen del juego.
     * @param savings Ahorros en porcentaje.
     * @param steamRatingPercent Puntuación sobre 100.
     * @param dealLink Link directo al juego rebajado.
     */
    public Juego(String title, String normalPrice, String salePrice, String thumb, String savings, String steamRatingPercent, String dealLink) {
        this.title = title;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.thumb = thumb;
        this.savings = savings;
        this.steamRatingPercent = steamRatingPercent;
        this.dealLink = dealLink;
    }

    //getters
    public String getTitle() { return title; }
    public String getNormalPrice() { return normalPrice; }
    public String getSalePrice() { return salePrice; }
    public String getThumb() { return thumb; }
    public String getSavings() { return savings; }
    public String getSteamRatingPercent() { return steamRatingPercent; }
    public String getDealLink() { return dealLink; }

    //setters
    public void setNormalPrice(String normalPrice) { this.normalPrice = normalPrice; }
    public void setSalePrice(String salePrice) { this.salePrice = salePrice; }
    public void setTitle(String title) { this.title = title; }
    public void setSavings(String savings) { this.savings = savings; }
    public void setSteamRatingPercent(String steamRatingPercent) { this.steamRatingPercent = steamRatingPercent; }
    public void setDealLink(String dealLink) { this.dealLink = dealLink; }
    public void setThumb(String thumb) { this.thumb = thumb; }
}