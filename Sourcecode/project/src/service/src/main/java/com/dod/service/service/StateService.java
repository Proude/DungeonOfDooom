package com.dod.service.service;

/**
 * Implements StateService
 */
public class StateService implements IStateService {

    IVisibilityService visibilityService;

    public StateService(IVisibilityService visibilityService) {
        this.visibilityService = visibilityService;
    }

    @Override
    public void GetState() {
        
    }

}