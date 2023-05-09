package bimentional.renderer;

import bimentional.GameObject;
import bimentional.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
  private final int maxBatchSize = 1000;
  private final List<RenderBatch> batches;

  public Renderer() {
    this.batches = new ArrayList<>();
  }

  public void add(GameObject gameObject) {
    SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);

    if (spriteRenderer != null) add(spriteRenderer);
  }

  private void add(SpriteRenderer spriteRenderer) {
    boolean added = false;
    for (RenderBatch batch : batches) {
      if (!batch.isFull()) {
        Texture texture = spriteRenderer.getTexture();
        if (texture == null || (batch.hasTexture(texture) || !batch.isTextureFull())) {
          batch.addSprite(spriteRenderer);
          added = true;
          break;
        }
      }
    }


    if (!added) {
      RenderBatch newBatch = new RenderBatch(maxBatchSize);
      batches.add(newBatch);
      newBatch.start();
      newBatch.addSprite(spriteRenderer);
    }
  }

  public void render() {
    for (RenderBatch batch : batches) {
      batch.render();
    }
  }
}
