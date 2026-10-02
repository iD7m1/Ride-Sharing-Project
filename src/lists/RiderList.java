package lists;

import datastructures.LinkedList;
import interfaces.IRider;
import interfaces.IRiderList;

public class RiderList implements IRiderList {
    private LinkedList<IRider> riders;

    @Override
    public boolean add(IRider rider) {
        return false;
    }

    @Override
    public IRider findById(int riderId) {
        return null;
    }

    @Override
    public LinkedList<IRider> findByName(String fullName) {
        return null;
    }

    @Override
    public IRider findByEmail(String email) {
        return null;
    }

    @Override
    public LinkedList<IRider> findByHomeCity(String homeCity) {
        return null;
    }

    @Override
    public LinkedList<IRider> getAll() {
        return null;
    }

    @Override
    public boolean removeById(int riderId) {
        return false;
    }

    @Override
    public boolean removeByEmail(String email) {
        return false;
    }

    @Override
    public int removeByName(String fullName) {
        return 0;
    }

    @Override
    public int removeByHomeCity(String homeCity) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}
