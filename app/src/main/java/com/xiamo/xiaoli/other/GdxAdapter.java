package com.xiamo.xiaoli.other;

import android.content.SharedPreferences;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.xiamo.xiaoli.XiaoliApplication;
import com.xiamo.xiaoli.utils.DpToPx;


public class GdxAdapter extends ApplicationAdapter {

    private OrthographicCamera camera;
    //private SpriteBatch batch;
    private PolygonSpriteBatch batch;
    private SkeletonRenderer renderer;
    private SkeletonRendererDebug debugRenderer;
    private TextureAtlas atlas;
    private Skeleton skeleton;
    private AnimationState state;
    private SkeletonJson json;
    private String path;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        //batch = new SpriteBatch();
        batch = new PolygonSpriteBatch();
        renderer = new SkeletonRenderer();
        //renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
        debugRenderer = new SkeletonRendererDebug();
        debugRenderer.setBoundingBoxes(false);
        debugRenderer.setRegionAttachments(false);


        String path = "spine/"+ConstantsKt.getSpinePath()+"/"+ConstantsKt.getSpinePath();
        // atlas = new TextureAtlas(Gdx.files.internal("goblins/goblins.atlas"));
        atlas = new TextureAtlas(Gdx.files.internal( path+".atlas"));
        json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
        json.setScale(ConstantsKt.getSpineScale());
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(path+".json"));
        // SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("goblins/goblins.json"));
        skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).

        float with = skeletonData.getWidth();
        skeleton.setPosition(with*ConstantsKt.getSpineScale()/2, 0f);

        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
        stateData.setMix("animation", "animation", 0.2f);
        //    stateData.setMix("jump", "walk", 0.2f);

        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
        state.setTimeScale(1.0f); // Slow all animations down to 50% speed.

        // Queue animations on track 0.
        state.setAnimation(0, "animation", true);

        state.addAnimation(0, "animation", true, 0); // Run after the jump.
    }


    @Override
    public void render() {
        state.update(Gdx.graphics.getDeltaTime()); // Update the animation time.

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glClearColor(0, 0, 0, 0);

        state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.

        // Configure the camera, SpriteBatch, and SkeletonRendererDebug.
        camera.update();

        batch.getProjectionMatrix().set(camera.combined);
        debugRenderer.getShapeRenderer().setProjectionMatrix(camera.projection);

        batch.begin();
        renderer.draw(batch, skeleton); // Draw the skeleton images.
        batch.end();




//        debugRenderer.draw(skeleton); // Draw debug lines.
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false); // Update camera with new size.
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }

    public void setAnimate() {
        setAnimate("animation");
        // setAnimate("walk"); // Run after the jump.
    }

    public void setAnimate(String animate) {
        state.addAnimation(0, animate, true, 0);
    }

    public void zoomBig() {
        camera.zoom = 0.5f;
    }

    public void zoomSmall() {
        camera.zoom = 1f;
    }

}
