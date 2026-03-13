
public class Member {
	public String Name;
	public String Id;
	public int MemberType;
	public int Ssn;
	public int DelayedReturnsCounter;
	public int SuspensionCounter;
	public boolean IsSuspended;
	/*
	memberType:
	 * 1. Undergraduate student
	 * 2. Postgraduate student
	 * 3. PhD student
	 * 4. Teacher
	 */

	public Member(String name, String id, int memberType, int ssn, int delayedReturnsCounter, int susppensionCounter, boolean isSuspended) {
		this.Name = name;
		this.Id = id;
		this.MemberType = memberType;
		this.Ssn = ssn;
		this.DelayedReturnsCounter = delayedReturnsCounter;
		this.SuspensionCounter = susppensionCounter;
		this.IsSuspended = isSuspended;
	}

	public Member() { //tillfällig, tas bort sen

	}

	public String getName() {
		return Name;
	}

	public String getId() {
		return Id;
	}

	public int getMemberType() {
		return MemberType;
	}

	public int getSsn() {
		return Ssn;
	}

	public int getDelayedReturnsCounter() {
		return DelayedReturnsCounter;
	}

	public void setDelayedReturnsCounter(int delayedReturnsCounter) {
		DelayedReturnsCounter = delayedReturnsCounter;
	}

	public int getSuspensionCounter() {
		return SuspensionCounter;
	}

	public void setSuspensionCounter(int suspensionCounter) {
		SuspensionCounter = suspensionCounter;
	}

	public boolean isSuspended() {
		return IsSuspended;
	}

	public void setSuspended(boolean suspended) {
		IsSuspended = suspended;
	}
}

