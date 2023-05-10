// **********************************************************************************
// Title: Serializable Comparator
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: SerializableComparator.java
// Description: Defines a SerializableComparator interface for making Comparators that
//    are Serializable
// **********************************************************************************
import java.io.Serializable;
import java.util.Comparator;

public interface SerializableComparator<T> extends Comparator<T>, Serializable {
}
