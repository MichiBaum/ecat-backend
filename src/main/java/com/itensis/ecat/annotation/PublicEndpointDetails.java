package com.itensis.ecat.annotation;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PublicEndpointDetails {

    private List<String> rawPaths;
    private List<String> antPaths;
    private Character character;
    private Numerus numerus;

    public PublicEndpointDetails(List<String> rawPaths, Character character, Numerus numerus){
        this.rawPaths = rawPaths;
        this.character = character;
        this.numerus = numerus;
        this.antPaths = rawPaths.stream()
                .map(this::generateAntPath)
                .collect(Collectors.toList());
    }

    private String generateAntPath(String rawPath) {
        if(this.character == Character.DIGIT && this.numerus == Numerus.PLURAL){
            return convertToAntPattern(rawPath, "{\\\\d+}");
        }
        if(this.character == Character.DIGIT && this.numerus == Numerus.SINGULAR){
            return convertToAntPattern(rawPath, "{\\\\d}");
        }
        if(this.character == Character.LETTER && this.numerus == Numerus.PLURAL){
            return null;
        }
        if(this.character == Character.LETTER && this.numerus == Numerus.SINGULAR){
            return null;
        }
        return rawPath;
    }

    private String convertToAntPattern(String rawPath, String replacement) {
        if(rawPath.contains("{") && rawPath.contains("}")){
            return rawPath.replaceAll("\\{.+}", replacement);
        }
        return rawPath;
    }

}
