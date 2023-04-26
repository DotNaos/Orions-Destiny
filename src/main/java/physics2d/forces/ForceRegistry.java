package physics2d.forces;

import physics2d.rigidbody.Rigidbody2D;

import java.util.ArrayList;
import java.util.List;

public class ForceRegistry {
    private List<ForceRegistration> registry;

    public ForceRegistry()
    {
        this.registry = new ArrayList<>();
    }

    public void add(Rigidbody2D rb, ForceGenerator fg)
    {
        ForceRegistration fr  = new ForceRegistration(fg, rb);
        registry.add(fr);
    }

    public void remove(Rigidbody2D rb, ForceGenerator fg)
    {
        ForceRegistration fr  = new ForceRegistration(fg, rb);
        registry.remove(fr);
    }

    public void clear()
    {
        registry.clear();
    }

    public void updateForces(float dt)
    {
        for (ForceRegistration fr : registry)
        {
            fr.fg.updateForce(fr.rb, dt);
        }
    }

    public void zeroForces()
    {
        for (ForceRegistration fr : registry)
        {
            // TODO: Implement zeroForces() in Rigidbody2D
//            fr.rb.zeroForces();
        }
    }
}
