package bimentional.renderer;

import bimentional.GameObject;
import bimentional.components.SpriteRender;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
  private final int maxBatchSize = 1000;
  private final List<RenderBatch> batches;

  public Renderer() {
    this.batches = new ArrayList<>();
  }

  public void add(GameObject gameObject) {
    SpriteRender spriteRender = gameObject.getComponent(SpriteRender.class);

    if (spriteRender == null) return;
    add(spriteRender);
  }

  private void add(SpriteRender spriteRender) {
    boolean added = false;
    for (RenderBatch batch : batches) {
      if (!batch.isFull()) {
        batch.addSprite(spriteRender);
        added = true;
        break;
      }
    }

    if (!added) {
      RenderBatch newBatch = new RenderBatch(maxBatchSize);
      newBatch.start();
      batches.add(newBatch);
      newBatch.addSprite(spriteRender);
    }
  }

  public void render() {
    for (RenderBatch batch : batches) {
      batch.render();
    }
  }
}
