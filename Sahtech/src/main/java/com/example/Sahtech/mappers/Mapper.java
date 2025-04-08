package com.example.Sahtech.mappers;

public interface Mapper<A,B> {

    B mapTo(A a); // DTO TO ENTITEE
    A mapFrom(B b); // LE CONTRAIRE
}

