package bimentional;

import bimentional.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
  private final String name;
  private final List<Component> components;
  public Transform transform = new Transform();

  public GameObject(String name) {
    this.name = name;
    this.components = new ArrayList<>();
    this.transform = new Transform();
  }

  public GameObject(String name, Transform transform) {
    this.name = name;
    this.components = new ArrayList<>();
    this.transform = transform;
  }

  public <T extends Component> void removeComponent(Class<T> componentClass) {
    for (Component component : components) {
      if (componentClass.isAssignableFrom(component.getClass())) {
        components.remove(component);
        return;
      }
    }
  }

  public <T extends Component> T getComponent(Class<T> componentClass) {
    for (Component component : components) {
      if (componentClass.isAssignableFrom(component.getClass())) {
        try {
          return componentClass.cast(component);
        } catch (ClassCastException e) {
          System.err.println("Error: Could not cast component to " + componentClass.getName());
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public void addComponent(Component component) {
    components.add(component);
    component.setGameObject(this);
  }

  public void update(float dt) {
    for (Component component : components) {
      component.update(dt);
    }
  }

  public void start() {
    for (Component component : components) {
      if (!component.started()) {
        component.start();
      }
    }
  }
}
