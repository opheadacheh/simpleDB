package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        Type fieldType;
        
        /**
         * The name of the field
         * */
        String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }
    
    private ArrayList<TDItem> Fields;
    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        // some code goes here
        return Fields.iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        Fields = new ArrayList<TDItem>();
        int i;
        for (i=0; i< typeAr.length; i++){
            TDItem field = new TDItem(typeAr[i], fieldAr[i]);
            Fields.add(field);
        }
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // some code goes here
        Fields = new ArrayList<TDItem>();
        int i;
        for (i=0; i< typeAr.length; i++){
            TDItem field = new TDItem(typeAr[i], "");
            Fields.add(field);
        }
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return Fields.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
        if ((i<0) || (i>Fields.size()-1))
            throw new NoSuchElementException("ith field is out of range");
        return Fields.get(i).fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
        if ((i<0) || (i>Fields.size()-1))
            throw new NoSuchElementException("ith field is out of range");
        return Fields.get(i).fieldType;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
        int i = 0;
        Iterator<TDItem> itr = Fields.iterator();
        while (itr.hasNext()){
            TDItem now;
            now = itr.next();
            if (now.fieldName.equals(name))
                return i;
            i++;
        }
        throw new NoSuchElementException("No name-matched field");
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
        int totalsize = 0;
        Iterator<TDItem> itr = this.iterator();
        while (itr.hasNext()){
            totalsize += itr.next().fieldType.getLen();
        }
        return totalsize;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here
        int totalsize = td1.numFields() + td2.numFields(), i = 0;
        Type[] TypeAr = new Type[totalsize];
        String[] NameAr = new String[totalsize];
        Iterator<TDItem> itr1 = td1.iterator(), itr2 = td2.iterator();
        while (itr1.hasNext()){
            TDItem x = itr1.next();
            TypeAr[i] = x.fieldType;
            NameAr[i] = x.fieldName;
            i++;
        }
        while (itr2.hasNext()){
            TDItem x = itr2.next();
            TypeAr[i] = x.fieldType;
            NameAr[i] = x.fieldName;
            i++;
        }
        return new TupleDesc(TypeAr, NameAr);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        // some code goes here
        if (o instanceof TupleDesc){
            TupleDesc x = (TupleDesc)o;
            if (x.numFields() != this.numFields()){
                return false;
            }else{
                Iterator<TDItem> itr1 = this.iterator(), itr2 = x.iterator();
                while (itr1.hasNext())
                    if (itr1.next().fieldType != itr2.next().fieldType)
                        return false;
                return true;
            }
        }else{
            return false;
        }
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here
        String ans = "";
        Iterator<TDItem> itr = this.iterator();
        while (itr.hasNext()){
            TDItem x = itr.next();
            ans += x.fieldType.toString() + "(" + x.fieldName.toString() + ")";
        }
        return ans;
    }
}
