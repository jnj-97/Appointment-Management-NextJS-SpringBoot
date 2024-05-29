"use client";
import cookies from "cookiesjs";
import { useEffect, useState } from "react";
import Modal from "react-modal";
import DatePicker from "react-datepicker";
import toast from "react-hot-toast";

export default function Home() {
  const [meetings, setMeetings] = useState([]);
  const [open, setOpen] = useState(false);
  const [meetingDate, setMeetingDate] = useState(new Date());
  const [users, setUsers] = useState([]);
  const [updateOpen, setUpdateOpen] = useState(false);
  const [meetingUpdate, setMeetingUpdate] = useState({
    description: "",
    organised_by: "",
    organised_for: "",
    date: new Date(),
  });
  const [meetingCreate, setMeetingCreate] = useState({
    description: "",
    organised_by: "",
    organised_for: "",
    date: new Date(),
  });

  const token = cookies("jwt");
  const customStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
    },
  };

  // Make sure to bind modal to your appElement (https://reactcommunity.org/react-modal/accessibility/)
  useEffect(() => {
    Modal.setAppElement("#root");
    async function initialData() {
      const res = await fetch("http://localhost:8080/meeting-list", {
        headers: { "auth-token": token },
      });

      if (!res.ok) {
        toast("Error getting meetings");
      }

      const data = await res.json();
      if (!!!data.status) {
        toast("Error getting meetings");
      } else {
        const username = cookies("username");
        setMeetings(data.meetings);
        const response = await fetch("http://localhost:8080/user-list", {
          headers: { "auth-token": token },
        });
        if (!response.ok!) {
          toast("Error getting list of users");
        } else {
          const users = await response.json();
          if (!!!users.status) {
            toast("Error getting list of users");
          } else {
            setUsers(users.users.map((user: string[]) => user != username));
          }
        }
      }
    }
    initialData();
  }, []);

  async function createMeeting(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const res = await fetch("http://localhost:8080/create-meeting", {
      headers: { "auth-token": token },
      method: "POST",
      body: JSON.stringify(meetingCreate),
    });
    if (!!!res.ok) {
      toast("Error creating meeting");
    }
    const response = await res.json();
    if (!!!res.status) {
      toast("Error creating meeting");
    } else {
      toast("Meeting created successfully!");
      setOpen(false);
    }
  }
  async function updateMeeting(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const res = await fetch("http://localhost:8080/update-meeting", {
      headers: { "auth-token": token },
      method: "POST",
      body: JSON.stringify(meetingUpdate),
    });
    if (!!!res.ok) {
      toast("Error creating meeting");
    }
    const response = await res.json();
    if (!!!res.status) {
      toast("Error updating meeting");
    } else {
      toast("Meeting updated successfully!");
      setOpen(false);
    }
  }

  async function deleteMeeting(id: string) {
    const res = await fetch("http://localhost:8080/delete-meeting", {
      headers: { "auth-token": token },
      method: "DELETE",
      body: JSON.stringify({ id: id }),
    });
    if (!!!res.ok) {
      toast("Error deleting meeting");
    }
    const response = await res.json();
    if (!!!response.status) {
      toast("Error deleting meeting");
    } else {
      toast("Meeting Deleted");
    }
  }

  return (
    <div id="root">
      <header className="sticky top-0 bg-gradient-to-br from-slate-400 to-gray-900 border-b-4 border-pink-400 w-full h-20 items-center">
        <div className="pt-4">
          <h1 className="text-xl font-semibold float-left ml-10">
            Meeting Management System
          </h1>
          <button className="float-right mr-10 p-4 rounded-lg bg-red-500">
            Log Out
          </button>
        </div>
      </header>
      <Modal
        isOpen={open}
        style={customStyles}
        contentLabel="Add Meeting"
        className="p-20 rounded-lg ml-[50%] mt-96 w-1/3 h-2/3 text-center justify-center items-center bg-pink-300"
      >
        <h1 className="text-center text-lg">Add Meeting</h1>
        <form onClick={(e) => createMeeting(e)} className="pt-20">
          <table>
            <tr>
              <td>
                <label>Description: </label>
              </td>
              <td>
                <textarea
                  name="desccription"
                  value={meetingCreate.description}
                  onChange={(e) =>
                    setMeetingCreate((p) => ({
                      ...p,
                      description: e.target.value,
                    }))
                  }
                />
              </td>
            </tr>
            <tr>
              <td>
                <label>Date: </label>
              </td>
              <td>
                <DatePicker
                  selected={meetingCreate.date}
                  showTimeSelect
                  onChange={(date: Date) => {
                    console.log(date);
                    setMeetingCreate((p) => ({ ...p, date: date }));
                  }}
                />
              </td>
            </tr>
            <tr>
              <td>
                <label>With: </label>
              </td>
              <td>
                <select
                  onChange={(e) =>
                    setMeetingCreate((p) => ({
                      ...p,
                      organised_by: e.target.value,
                    }))
                  }
                >
                  {!!users.length ? (
                    users.map((user: { username: string }) => {
                      return (
                        <option value={user.username}>{user.username}</option>
                      );
                    })
                  ) : (
                    <option
                      value="hi"
                      onClick={() => {
                        toast("no other users exist");
                        setOpen(false);
                      }}
                    >
                      NA
                    </option>
                  )}
                </select>
              </td>
            </tr>
          </table>
          <div className="w-full flex justify-around">
            <button
              className=" h-16 p-10 rounded-lg bg-green-400"
              type="submit"
            >
              Create Meeting
            </button>
            <button
              className="h-16 p-10 rounded-lg bg-red-600"
              onClick={() => setOpen(false)}
            >
              Close
            </button>
          </div>
        </form>
      </Modal>

      <Modal
        isOpen={updateOpen}
        style={customStyles}
        contentLabel="Update Meeting"
        className="p-20 rounded-md w-full h-full"
      >
        <h1 className="text-center text-lg">Update Meeting</h1>
        <form onClick={(e) => updateMeeting(e)} className="pt-20">
          <label>Description: </label>
          <textarea
            name="desccription"
            value={meetingUpdate.description}
            onChange={(e) =>
              setMeetingUpdate((p) => ({ ...p, description: e.target.value }))
            }
          />
          <label>Date: </label>
          <DatePicker
            selected={meetingUpdate.date}
            showTimeSelect
            onChange={(date: Date) => {
              console.log(date);
              setMeetingUpdate((p) => ({ ...p, date: date }));
            }}
          />
          <label>With: </label>
          <select
            value={meetingUpdate.organised_by}
            onChange={(e) =>
              setMeetingUpdate((p) => ({ ...p, organised_by: e.target.value }))
            }
          >
            {!!users.length ? (
              users.map((user: { username: string }) => {
                return <option value={user.username}>{user.username}</option>;
              })
            ) : (
              <option
                value="hi"
                onClick={() => {
                  toast("no other users exist");
                  setOpen(false);
                }}
              >
                NA
              </option>
            )}
          </select>
          <div className="w-full flex justify-around">
            <button
              className=" h-16 p-10 rounded-lg bg-green-400"
              type="submit"
            >
              Update Meeting
            </button>
            <button
              className="h-16 p-10 rounded-lg bg-red-600"
              onClick={() => setOpen(false)}
            >
              Close
            </button>
          </div>
        </form>
      </Modal>
      <div className="flex">
        <button
          className="rounded-lg bg-green-300 p-4 mt-5 float-left "
          onClick={() => {
            setOpen(true);
          }}
        >
          Create new meeting
        </button>
      </div>
      {!!meetings.length ? (
        <table>
          <tr>
            <th>SI</th>
            <th>Description</th>
            <th>Organised by</th>
            <th>Organised for</th>
            <th>Time and Date</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
          {meetings.map(
            (
              meeting: {
                id: string;
                description: string;
                organised_by: string;
                organised_for: string;
                date: Date;
              },
              index: number
            ) => {
              return (
                <tr key={meeting.id}>
                  <td>{index}</td>
                  <td>{meeting.description}</td>
                  <td>{meeting.organised_by}</td>
                  <td>{meeting.organised_for}</td>
                  <td>{meeting.date.toString()}</td>
                  <td>
                    <button
                      onClick={() => setMeetingUpdate(meeting)}
                      className="h-10 bg-orange-400 p-4"
                    >
                      Update
                    </button>
                  </td>
                  <td>
                    <button
                      onClick={() => deleteMeeting(meeting.id)}
                      className="h-10 bg-red-500 p-4"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              );
            }
          )}
        </table>
      ) : (
        <h1 className="text-lg text-center text-green-500">
          No Upcoming Meetings!
        </h1>
      )}
    </div>
  );
}
