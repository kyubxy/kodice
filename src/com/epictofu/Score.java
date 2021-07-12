package com.epictofu;

public class Score
{
   public int u;
   public int m;
   public int c;

    public Score (int n)
    {
        this.u = n;
        this.m = n;
        this.c = n;
    }
   public Score (int u, int m, int c)
   {
       this.u = u;
       this.m = m;
       this.c = c;
   }

   public int getTotal ()
   {
       return u + m + c;
   }

   public void addWith (Score s)
   {
       this.u = s.u;
       this.m = s.m;
       this.c = s.c;
   }

   public static Score ZeroScore = new Score(0,0,0);
}

