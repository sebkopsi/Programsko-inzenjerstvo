import { useLoaderData } from "react-router";
import "./adminInbox.css";

type RequestSummary = {
  id: number;
  title: string;
  type: string;
  status: string;
  sender: string;
  time: string;
};

export default function AdminInboxContent() {
  const { requests } = useLoaderData<{ requests: RequestSummary[] }>();

  const statusColors: Record<string, string> = {
    Pending: "#f0ed49d7",
    Approved: "#10B981",
    Declined: "#EF4444",
  };

  return (
    <section id="content">
      <div className="header-wrapper">
        <header className="admin-header">
          <h1>Admin Inbox</h1>
        </header>
      </div>

      <div className="inbox-list">
        {/* Inbox table headers always shown */}
        <div className="inbox-specifications-header">
          <div className="inbox-title-header">Title</div>
          <div className="inbox-type-header">Type</div>
          <div className="inbox-sender-header">Sender</div>
          <div className="inbox-time-header">Time</div>
          <div className="inbox-status-header">Status</div>
        </div>

        {/* Render each request if present */}
        {requests.length > 0 ? (
          requests.map((mail) => (
            <div key={mail.id} className="inbox-item">
              <div className="inbox-title">{mail.title}</div>
              <div className="inbox-type">{mail.type}</div>
              <div className="inbox-sender">{mail.sender}</div>
              <div className="inbox-time">{mail.time}</div>
              <div
                className="inbox-status"
                style={{ backgroundColor: statusColors[mail.status] || "#ccc" }}
              >
                {mail.status}
              </div>
            </div>
          ))
        ) : (
          <div className="inbox-item no-requests">
            <div style={{ gridColumn: "1 / -1", textAlign: "center", padding: "1rem" }}>
              No requests.
            </div>
          </div>
        )}
      </div>
    </section>
  );
}
