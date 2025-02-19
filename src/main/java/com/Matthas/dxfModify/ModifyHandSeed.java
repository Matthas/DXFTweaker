package com.Matthas.dxfModify;

import com.Matthas.dxfRead.DXFDrawing;

//function to modify HandSeed in DXF
//It must be called if any objects have been added to DXF otherwise AutoCAD will refuse to open file.

public class ModifyHandSeed {
    static String[] ModifyHandSeed(String[] arylines, DXFDrawing CAD) {
        int HandseedIndex = CAD.getHandSeedIndex() + 1;
        arylines[HandseedIndex] = CAD.getNextSeed();
        //add code to modify handseed
        return arylines;
    }
}
