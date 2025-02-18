package org.sergedb.processing.twodprimitives;

import org.sergedb.processing.core.PCore;
import org.sergedb.processing.core.ServiceManager;
import org.sergedb.processing.core.managers.ScreenManager;

public class MainApp extends PCore {

    private ScreenManager screenManager;

    @Override
    protected void onInitialize() {
        ServiceManager.getInstance().getSettingsManager().setCoordinatesDisplay(true).setFPSDisplay(true);

        screenManager = ServiceManager.getInstance().getScreenManager();
        screenManager.pushScreen(new TeddyBearScreen(this));
    }

    @Override
    protected void onUpdate() {
        screenManager.update();
    }

    @Override
    protected void onRender() {
        screenManager.render();
    }

    public static void main(String[] args) {
        launch(MainApp.class);
    }
}
