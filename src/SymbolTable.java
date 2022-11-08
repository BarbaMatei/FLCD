import java.util.ArrayList;

public class SymbolTable {
    private final Integer _size;
    private final ArrayList<ArrayList<String>> _table;

    public SymbolTable(Integer initialSize)
    {
        this._size = initialSize;
        this._table = new ArrayList<>();
        for (int i = 0; i < this._size; i++) {
            this._table.add(new ArrayList<>());
        }
    }

    public boolean AddElement(String term)
    {
        boolean wasElementAdded = false;
        if(!this.ContainsElement(term))
        {
            int key = this.HashFunction(term);
            this._table.get(key).add(term);
            wasElementAdded = true;
        }
        return wasElementAdded;
    }

    public Pair<Integer, Integer> FindTermPosition(String term)
    {
        int key = this.HashFunction(term);
        if (!this._table.get(key).isEmpty())
        {
            ArrayList<String> collisionList = this._table.get(key);
            for (int i = 0; i < collisionList.size(); i++)
            {
                if (collisionList.get(i).equals(term))
                {
                    return new Pair<>(key, i);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        for (int i = 0; i < this._table.size(); i++)
        {
            if (!this._table.get(i).isEmpty())
            {
                representation.append(i).append(" - ").append(this._table.get(i)).append("\n");
            }
        }
        return representation.toString();
    }

    private int HashFunction(String term)
    {
        int asciiSum = 0;
        for(int i = 0 ; i < term.length();i++)
        {
            asciiSum = asciiSum + term.charAt(i);
        }
        return asciiSum % this._size;
    }

    private boolean ContainsElement(String term)
    {
        int key = this.HashFunction(term);
        boolean doesTermExist = false;
        ArrayList<String> collisionList = this._table.get(key);
        for(int i = 0 ; i < collisionList.size(); i++)
        {
            String collisionElement = collisionList.get(i);
            if(collisionElement.equals(term))
            {
                doesTermExist = true;
                break;
            }
        }
        return doesTermExist;
    }
}
