package com.fortune.util;

import com.fortune.datastructures.DCELVertex;
import org.jetbrains.annotations.NotNull;

public abstract class ArcKey implements Comparable<ArcKey> {
    protected abstract DCELVertex getLeft();

    protected abstract DCELVertex getRight();

    @Override
    public int compareTo(@NotNull ArcKey arcKey) {
        DCELVertex mLeft = this.getLeft();
        DCELVertex mRight = this.getRight();
        DCELVertex yLeft = arcKey.getLeft();
        DCELVertex yRight = arcKey.getRight();

        if (((arcKey.getClass() == ArcQuery.class) || (this.getClass() == ArcQuery.class)) &&
                ((mLeft.getCoordinates().x <= yLeft.getCoordinates().x && mRight.getCoordinates().x >= yRight.getCoordinates().x) ||
                        (yLeft.getCoordinates().x <= mLeft.getCoordinates().x && yRight.getCoordinates().x >= mRight.getCoordinates().x))) {
            return 0;
        }

        if (mLeft.getCoordinates().x == yLeft.getCoordinates().x &&
                mRight.getCoordinates().x == yRight.getCoordinates().x) return 0;
        if (mLeft.getCoordinates().x >= yRight.getCoordinates().x) return 1;
        if (mRight.getCoordinates().x <= yLeft.getCoordinates().x) return -1;

        return Vector2D.midpoint(mLeft.getCoordinates(), mRight.getCoordinates())
                .compareTo(Vector2D.midpoint(yLeft.getCoordinates(), yRight.getCoordinates()));
    }
}
