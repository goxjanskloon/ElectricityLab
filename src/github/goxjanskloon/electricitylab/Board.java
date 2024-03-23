package github.goxjanskloon.electricitylab;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
public class Board{
    public interface ModifyListener<T extends ModifyListener<T>> extends Comparable<T>{void modified(Block b);}
    ConcurrentSkipListSet<ModifyListener<?>> modifyListeners;
    public boolean addModifyListener(ModifyListener<?> l){return modifyListeners.add(l);}
    public boolean removeModifyListener(ModifyListener<?> l){return modifyListeners.remove(l);}
    public void callModifyListeners(Block b){for(ModifyListener<?> l:modifyListeners) l.modified(b);}
    public enum Direction{
        UP(0,-1),RIGHT(1,0),DOWN(0,1),LEFT(-1,0);
        public final int xOffset,yOffset;
        Direction(int xOffset,int yOffset){this.xOffset=xOffset;this.yOffset=yOffset;}
        Direction valueOf(int direction){
            return switch(direction){
                case 0->UP;
                case 1->RIGHT;
                case 2->DOWN;
                case 3->LEFT;
                default->null;
            };
        }
    }
    public abstract class Block{
        public final int x,y;
        Block(int x,int y){this.x=x;this.y=y;}
    }
    ArrayList<ArrayList<Block>> blocks=new ArrayList<>();
    public Block getBlock(int x,int y){return blocks.get(y).get(x);}
    public class Void extends Block{Void(int x,int y){super(x,y);}}
    public abstract class ValuedBlock extends Block{
        AtomicInteger voltage,resistance;
        ValuedBlock(int x,int y){super(x,y);}
        void setResistance(int r){if(resistance.getAndSet(r)!=r) callModifyListeners(this);}
        int getResistance(){return resistance.get();}
    }
    public interface Flushable{void flush(boolean alwaysCallModifyListeners);}
    public class Traverse extends ValuedBlock implements Flushable{
        Traverse(int x,int y){super(x,y);}
        boolean[] connected=new boolean[4];
        void setConnected(Direction d,boolean connected){
            boolean modified=this.connected[d.ordinal()]!=connected;
            this.connected[d.ordinal()]=connected;
            if(modified) flush(true);
        }
        boolean isConnected(Direction d){return connected[d.ordinal()];}
        @Override
        public void flush(boolean alwaysCallModifyListeners){

        }
    }
}
