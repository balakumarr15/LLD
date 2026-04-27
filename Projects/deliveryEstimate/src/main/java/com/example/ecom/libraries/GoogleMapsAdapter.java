package com.example.ecom.libraries;

import com.example.ecom.libraries.models.GLocation;

public class GoogleMapsAdapter implements MapsApi {
    private GoogleMapsApi googleMapsApi;

    public GoogleMapsAdapter() {
        this.googleMapsApi = new GoogleMapsApi();
    }
    @Override
    public int estimate(GLocation src, GLocation dest) {
        return googleMapsApi.estimate(src, dest);
    }
}
