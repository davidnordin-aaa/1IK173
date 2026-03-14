import java.util.Date;

public class Member {
	public int Id;
	public String FirstName;
	public String LastName;
	public int MemberType;
	public Long Ssn;
	public int DelayedReturnsCounter;
	public int SuspensionCounter;
	public boolean IsSuspended;
	public Date SuspensionEndDate;

	public Member() {}

	public Member(int id, String firstName, String lastName, int memberType, Long ssn, int delayedReturnsCounter, int suspensionCounter, boolean isSuspended, Date suspensionEndDate) {
		this.Id = id;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.MemberType = memberType;
		this.Ssn = ssn;
		this.DelayedReturnsCounter = delayedReturnsCounter;
		this.SuspensionCounter = suspensionCounter;
		this.IsSuspended = isSuspended;
		this.SuspensionEndDate = suspensionEndDate;
	}

	public String getFirstName() {
		return FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public int getId() {
		return Id;
	}

	public int getMemberType() {
		return MemberType;
	}

	public Long getSsn() {
		return Ssn;
	}

	public int getDelayedReturnsCounter() {
		return DelayedReturnsCounter;
	}

	public void setDelayedReturnsCounter(int delayedReturnsCounter) {
		this.DelayedReturnsCounter = delayedReturnsCounter;
	}

	public int getSuspensionCounter() {
		return SuspensionCounter;
	}

	public void setSuspensionCounter(int suspensionCounter) {
		this.SuspensionCounter = suspensionCounter;
	}

	public boolean isSuspended() {
		return IsSuspended;
	}

	public void setSuspended(boolean suspended) {
		this.IsSuspended = suspended;
	}

	public Date getSuspensionEndDate() {
		return SuspensionEndDate;
	}

	public void setSuspensionEndDate(Date suspensionEndDate) {
		this.SuspensionEndDate = suspensionEndDate;
	}
}

