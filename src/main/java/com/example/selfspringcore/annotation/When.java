package com.example.selfspringcore.annotation;

public enum When {
    /** S is a subset of T */
    ALWAYS,
    /** nothing definitive is known about the relation between S and T */
    UNKNOWN,
    /** S intersection T is non empty and S - T is nonempty */
    MAYBE,
    /** S intersection T is empty */
    NEVER;
}
