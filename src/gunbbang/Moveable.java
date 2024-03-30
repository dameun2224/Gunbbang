package gunbbang;

public interface Moveable {
	public abstract void left();
	public abstract void right();
	public abstract void up();
	default public void down() {};
	public void attack();
	default public void attack(Enemy enemy) {}; 
}
