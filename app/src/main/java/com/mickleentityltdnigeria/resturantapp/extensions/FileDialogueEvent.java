package com.mickleentityltdnigeria.resturantapp.extensions;

@FunctionalInterface
public interface FileDialogueEvent {
    void invoke(String filePath);
}
