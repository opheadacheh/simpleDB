package simpledb;

import java.io.*;
import java.util.*;

public class Table {
//Self-defined class
    private DbFile File;
    private String Name;
    private String PkeyFields;

    public Table(DbFile file, String name, String pkeyFields) {
        File = file;
        Name = name;
        PkeyFields = pkeyFields;
    }

    public DbFile getFile() {
        return File;
    }

    public String getName() {
        return Name;
    }

    public String getPkey() {
        return PkeyFields;
    }
}

