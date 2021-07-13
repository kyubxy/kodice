package com.epictofu;

public class Score
{
   public double u;
   public double m;
   public double c;

    public Score (double n)
    {
        this.u = n;
        this.m = n;
        this.c = n;
    }
   public Score (double u, double m, double c)
   {
       this.u = u;
       this.m = m;
       this.c = c;
   }

   public Double getTotal ()
   {
       return u + m + c;
   }

    public void multiplyWith (Score s)
    {
        this.u *= s.u;
        this.m *= s.m;
        this.c *= s.c;
    }

   public void addWith (Score s)
   {
       this.u += s.u;
       this.m += s.m;
       this.c += s.c;
   }

    public void modScore ()
    {
        u = mod (u);
        m = mod (m);
        c = mod (c);
    }

    private Double mod (Double n)
    {
        if (n < 0)
            n *= -1;

        return n;
    }

    @Override
    public String toString()
    {
        return "Total: " + getTotal() +
               " u: " + Double.toString(u) +
               " m: " + Double.toString(m) +
               " c: " + Double.toString(c);
    }

    @Override
   public boolean equals (Object o)
   {
       // If the object is compared with itself then return true
       if (o == this) {
           return true;
       }

       if (!(o instanceof Score)) {
           return false;
       }

       Score c = (Score) o;

       return c.toString().equals(toString());
   }
}

