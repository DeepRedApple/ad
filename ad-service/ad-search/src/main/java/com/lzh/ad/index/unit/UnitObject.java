package com.lzh.ad.index.unit;

import com.lzh.ad.index.plan.PlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitObject {

	private Long unitId;
	private Integer status;
	private Integer positionType;
	private Long planId;

	private PlanObject planObject;

	public void update(UnitObject object) {
		if (null != object.getUnitId()) {
			this.unitId = object.getUnitId();
		}
		if (null != object.getStatus()) {
			this.status = object.getStatus();
		}
		if (null != object.getPositionType()) {
			this.positionType = object.getPositionType();
		}
		if (null != object.getPlanId()) {
			this.planId = object.getPlanId();
		}
		if (null != object.getPlanObject()) {
			this.planObject = object.getPlanObject();
		}
	}

	public static boolean isSlotTypeOk(int slotType, int positionType) {
		switch (slotType) {
			case UnitConstants.POSITION_TYPE.KAIPING:
				return isKaiPing(positionType);
			case UnitConstants.POSITION_TYPE.TIEPING:
				return isTiePian(positionType);
			case UnitConstants.POSITION_TYPE.TIEPING_MIDDLE:
				return isTiePianMiddle(positionType);
			case UnitConstants.POSITION_TYPE.TIEPING_PAUSE:
				return isTiePianPause(positionType);
			case UnitConstants.POSITION_TYPE.TIEPING_POST:
				return isTiePianPost(positionType);
			default:
				return false;
		}
	}

	private static boolean isKaiPing(int positionType) {
		return (positionType & UnitConstants.POSITION_TYPE.KAIPING) > 0;
	}

	private static boolean isTiePian(int positionType) {
		return (positionType & UnitConstants.POSITION_TYPE.TIEPING) > 0;
	}

	private static boolean isTiePianPause(int positionType) {
		return (positionType & UnitConstants.POSITION_TYPE.TIEPING_PAUSE) > 0;
	}

	private static boolean isTiePianMiddle(int positionType) {
		return (positionType & UnitConstants.POSITION_TYPE.TIEPING_MIDDLE) > 0;
	}

	private static boolean isTiePianPost(int positionType) {
		return (positionType & UnitConstants.POSITION_TYPE.TIEPING_PAUSE) > 0;
	}
}
