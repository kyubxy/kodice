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

   public static Score modScore (Score s)
   {
        return new Score(mod(s.u), mod(s.m), mod(s.c));
   }

   public static Double mod (Double n)
   {
       if (n < 0)
           n *= -1;

       return n;
   }
}

