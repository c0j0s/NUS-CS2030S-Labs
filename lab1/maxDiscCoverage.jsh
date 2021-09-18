Circle createUnitCircle(Point p, Point q){
    //midpoint
    Point m = p.midPoint(q);

    //angle to x-axis;
    double theta = p.angleTo(q);

    //d = sqrt(1^2 - (p->m)^2)
    double d = Math.sqrt(1 - Math.pow(p.distanceTo(m),2));
    
    return new Circle(m.moveTo(theta + Math.PI/2, d),1);
}

int findMaxDiscCoverage(Point[] points){
    int maxDiscCoverage = 0;

    for (int i = 0; i < points.length - 1; i++) {
        for (int j = i + 1; j < points.length; j++) {
            //skip points if d > 2
            if(points[i].distanceTo(points[j]) > 2) continue;

            // find coverage with (points[i], points[j])
            Circle c = createUnitCircle(points[i], points[j]);
            
            int pointsInCircle = 0;
            for(int x = 0; x < points.length; x++){
                if(c.isPointWithin(points[x])) pointsInCircle++;
            }

            if(pointsInCircle > maxDiscCoverage) maxDiscCoverage = pointsInCircle;
        }
     }
    return maxDiscCoverage;
}
