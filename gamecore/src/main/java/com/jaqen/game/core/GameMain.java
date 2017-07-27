package com.jaqen.game.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;

/**
 * @author chenp
 * @version 2017-07-26 13:10
 */

public class GameMain extends ApplicationAdapter {
    private PerspectiveCamera camera;
    private CameraInputController camController;
    private Environment environment;
    private ModelBatch modelBatch;
    private AssetManager assetManager;
    private Array<ModelInstance> instances = new Array<>();
    private boolean isLoading = false;

    @Override
    public void create () {
        camera = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camController = new CameraInputController(camera);
        environment = new Environment();
        modelBatch = new ModelBatch();
        assetManager = new AssetManager();

        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        Gdx.input.setInputProcessor(camController);

        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        assetManager.load("model/monu9.obj", Model.class);

        isLoading = true;
    }

    private void doneLoading() {
        Model model = assetManager.get("model/monu9.obj", Model.class);
        ModelInstance instance = new ModelInstance(model);

        instance.transform.scl(0.1f);
        instances.add(instance);
        isLoading = false;
    }

    @Override
    public void render () {
        if (isLoading && assetManager.update())
            doneLoading();

        camController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        assetManager.dispose();
    }
}
