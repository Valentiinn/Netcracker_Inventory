package com.netcracker.edu.inventory.segment.impl;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.segment.Segment;
import com.netcracker.edu.inventory.service.Service;
import com.netcracker.edu.inventory.service.impl.ServiceImpl;

import java.util.*;

import static com.netcracker.edu.inventory.model.FeelableEntity.*;

public class SegmentImpl implements Segment {

    private Service service = new ServiceImpl();

    private Map<Unique.PrimaryKey, Unique> elements = new TreeMap();

    @Override
    public boolean add(Unique element) {
        if (validationElement(element))  {
            Unique tempElement = service.getIndependentCopy(element);
            Unique.PrimaryKey pk = element.getPrimaryKey();
            if(pk != null) {
                if(!elements.containsKey(pk)) {
                    elements.put(pk, tempElement);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean set(Unique element) {
        if (validationElement(element)) {
            Unique tempElement = service.getIndependentCopy(element);
            Unique.PrimaryKey pk = element.getPrimaryKey();
            if(pk != null) {
                if(elements.containsKey(pk)) {
                    elements.put(pk, tempElement);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Unique get(Unique.PrimaryKey pk) {
        if (pk != null) {
            return elements.get(pk);
        }
        return null;
    }

    @Override
    public boolean put(Unique element) {
        if (validationElement(element)) {
            Unique tempElement = service.getIndependentCopy(element);
            Unique.PrimaryKey pk = element.getPrimaryKey();
            if(pk != null) {
                if(elements.containsKey(pk)) {
                    set(tempElement);
                    return true;
                } else {
                    return add(tempElement);
                }
            }
        }
        return false;
    }

    @Override
    public boolean contain(Unique.PrimaryKey pk) {
        if (validationPK(pk)) {
            return elements.containsKey(pk);
        } else {
            return false;
        }
    }

    @Override
    public Collection<Unique> getGraph() {
        Map<Unique.PrimaryKey, Unique> graph = new TreeMap();
        for (Unique.PrimaryKey pk : elements.keySet()) {
            Unique element = elements.get(pk);
            if(element != null) {
                graph.put(pk, service.getIndependentCopy(element));
            }
        }

        for(Unique.PrimaryKey pk : graph.keySet()) {
            Unique element = graph.get(pk);
            if(element != null) {
                graph.put(pk, resetUnique(element, graph));
            }
        }

        return graph.values();
    }

    private Unique resetUnique(Unique element, Map<Unique.PrimaryKey, Unique> graph) {
        List<Field> fields = null;
        fields = ((FeelableEntity) element).getAllFieldsList();
        for(Field field : fields) {
            if (field.getType() == Connection.class) {
                Connection tempConnection = (Connection) field.getValue();
                Unique.PrimaryKey tempPK = tempConnection.getPrimaryKey();
                if (graph.containsKey(tempPK)) {
                    field.setValue(graph.get(tempPK));
                }
            } else if (field.getType() == Device.class) {
                if (field.getValue() != null) {
                    Device tempDevice = (Device) field.getValue();
                    Unique.PrimaryKey tempPK = tempDevice.getPrimaryKey();
                    if (graph.containsKey(tempPK)) {
                        field.setValue(graph.get(tempPK));
                    }
                }
            } else if (field.getType() == List.class || field.getType() == Set.class) {
                Collection<Unique> uniqueFields = (Collection) field.getValue();
                List<Unique> tempUniqueFields = new ArrayList<Unique>();
                for(Unique unique : uniqueFields) {
                    if(unique != null) {
                        Unique.PrimaryKey tempPK = unique.getPrimaryKey();
                        if (graph.containsKey(tempPK)) {
                            tempUniqueFields.add(graph.get(tempPK));
                        } else {
                            tempUniqueFields.add((Unique) tempPK);
                        }
                    } else {
                        tempUniqueFields.add(null);
                    }
                }
                if(field.getType() == Set.class) {
                    field.setValue(new HashSet<Unique>(tempUniqueFields));
                } else {
                    field.setValue(tempUniqueFields);
                }
            }
        }

        ((FeelableEntity) element).fillAllFields(fields);
        return element;
    }

    private boolean validationElement(Unique element) {
        if (element != null && !Rack.class.isInstance(element) && !Unique.PrimaryKey.class.isInstance(element))  {
            return true;
        }
        return false;
    }

    private boolean validationPK(Unique.PrimaryKey primaryKey) {
        if (primaryKey != null && !RackPrimaryKey.class.isInstance(primaryKey))  {
            return true;
        }
        return false;
    }
}