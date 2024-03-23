package github.goxjanskloon.electricitylab;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
public class Board{
    public interface ModifyListener<T extends ModifyListener<T>> extends Comparable<T>{void modified(Block b);}
    private ConcurrentSkipListSet<ModifyListener<?>> modifyListeners;
    public boolean addModifyListener(ModifyListener<?> l){return modifyListeners.add(l);}
    public boolean removeModifyListener(ModifyListener<?> l){return modifyListeners.remove(l);}
    public void callModifyListeners(Block b){for(ModifyListener<?> l:modifyListeners) l.modified(b);}
    public abstract class Block{
        public final int x,y;
        Block(int x,int y){this.x=x;this.y=y;}
    }
    public class Void extends Block{Void(int x,int y){super(x,y);}}
    public abstract class ValuedBlock extends Block{
        private AtomicInteger voltage,resistance;
        ValuedBlock(int x,int y){super(x,y);}
        void setResistance(int r){if(resistance.getAndSet(r)!=r) callModifyListeners(this);}
        int getResistance(){return resistance.get();}
    }
    public interface Flushable{void flush();}
    public interface Faced{
        enum Facing{UP,DOWN,LEFT,RIGHT};
        Facing getFacing();
        void setFacing(Facing f);
    }
    public class Traverse extends ValuedBlock implements Flushable,Faced{
        Traverse(int x,int y){super(x,y);}
        @Override
        public void flush(){
            
        }
        @Override
        public Facing getFacing(){return null;}
        @Override
        public void setFacing(Facing f){
        }
    }
}
