package DXFModify;

import DXFRead.DXFDrawing;

//function to modify HandSeed in DXF
//It must be called if any objects have been added to DXF otherwise AutoCAD will refuse to open file.

public class ModifyHandSeed {
    static String[] ModifyHandSeed(String[] arylines, DXFDrawing CAD) {
        int HandseedIndex = CAD.getHandSeedIndex() + 2;
        arylines[HandseedIndex] = CAD.getNextSeed();
        //add code to modify handseed
        return arylines;
    }
}
