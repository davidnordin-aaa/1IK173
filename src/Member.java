import java.util.Date;

public class Member {
	public String Name;
	public int Id;
	public int MemberType;
	public int Ssn;
	public int DelayedReturnsCounter;
	public int SuspensionCounter;
	public boolean IsSuspended;
	public Date SuspensionEndDate;
	/*
	memberType:
	 * 1. Undergraduate student
	 * 2. Postgraduate student
	 * 3. PhD student
	 * 4. Teacher
	 */

	public Member(String name, int id, int memberType, int ssn, int delayedReturnsCounter, int suspensionCounter, boolean isSuspended, Date suspensionEndDate) {
		this.Name = name;
		this.Id = id;
		this.MemberType = memberType;
		this.Ssn = ssn;
		this.DelayedReturnsCounter = delayedReturnsCounter;
		this.SuspensionCounter = suspensionCounter;
		this.IsSuspended = isSuspended;
		this.SuspensionEndDate = suspensionEndDate;
	}

	public Member() { //tillfällig, tas bort sen

	}

	public String getName() {
		return Name;
	}

	public int getId() {
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

	public Date getSuspensionEndDate() {
		return SuspensionEndDate;
	}

	public void setSuspensionEndDate(Date suspensionEndDate) {
		SuspensionEndDate = suspensionEndDate;
	}
}

