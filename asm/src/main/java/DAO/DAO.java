package DAO;

abstract public class DAO<Entity, Key> {
	abstract public void create(Entity entity);
	abstract public void update(Entity entity);
	abstract public void remove(Entity entity);
	abstract public Entity findById(Key id);
}
