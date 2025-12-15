<h1>Sha’eerah – Hajj Nutrition & Inspection Management System</h1>

<p>
Sha’eerah is a backend system designed to manage pilgrim nutrition, kitchen compliance,
and inspection workflows during the Hajj season.
</p>

<hr/>

<h2>Team Contributions</h2>

<!-- ================= Muath ================= -->
<h3>Muath’s Contributions</h3>
<ul>
  <li>Designed and implemented core domain models and database structure:
    <ul>
      <li>Pilgrim</li>
      <li>Campaign</li>
      <li>Inspector</li>
      <li>Violation</li>
      <li>Objection</li>
    </ul>
  </li>

  <li>Designed the complete database diagram and defined entity relationships.</li>

  <li>Implemented full business logic for violations and objections:
    <ul>
      <li>Linking violations to inspectors and kitchens</li>
      <li>Allowing only one objection per violation</li>
      <li>Approving and rejecting objections with proper status handling</li>
      <li>Automatically closing violations when objections are approved</li>
    </ul>
  </li>

  <li>Developed violation enforcement rules:
    <ul>
      <li>Automatically suspending kitchens when violations exceed a defined threshold</li>
      <li>Managing violation lifecycle (open, close, reopen)</li>
    </ul>
  </li>

  <li>Built and integrated the emergency reporting feature using n8n:
    <ul>
      <li>Designed n8n workflows for emergency cases</li>
      <li>Created a backend service to communicate with n8n</li>
      <li>Sent real-time emergency reports to admins via Telegram bot</li>
      <li>Broadcasted emergency messages to all admins using stored Telegram chat IDs</li>
    </ul>
  </li>

  <li>Implemented Admin Telegram integration:
    <ul>
      <li>Stored Telegram chatId in Admin model</li>
      <li>Provided endpoints to retrieve admin chat IDs</li>
      <li>Linked backend data dynamically with n8n Telegram nodes</li>
    </ul>
  </li>
</ul>

<hr/>

<!-- ================= Rawan ================= -->
<h3>Rawan’s Contributions</h3>

<ul>
  <li><strong>Kitchen</strong>
    <ul>
      <li>Full CRUD operations</li>
      <li>Additional endpoints</li>
      <li>Summarized kitchen reports</li>
      <li>Output: KitchenReportDTO</li>
    </ul>
  </li>

  <li><strong>Meal</strong>
    <ul>
      <li>Full CRUD operations</li>
      <li>Additional endpoints</li>
    </ul>
  </li>

  <li><strong>Rating</strong>
    <ul>
      <li>Kitchen evaluations</li>
      <li>Full CRUD operations</li>
    </ul>
  </li>

  <li><strong>Email Service</strong>
    <ul>
      <li>Sends automated email notifications</li>
      <li>Notifies pilgrims upon successful campaign registration</li>
      <li>Includes campaign details in confirmation emails</li>
    </ul>
  </li>

  <li><strong>AI – Multi-Language Health Advice</strong>
    <ul>
      <li>Generates AI-based general health and nutrition advice</li>
      <li>Supports multiple languages</li>
      <li>Endpoint: <code>GET /advice/{lang}</code></li>
      <li>Output: PilgrimAdviceDTO</li>
    </ul>
  </li>

  <li><strong>AI – Campaign Health Risk Prediction</strong>
    <ul>
      <li>Analyzes campaign-level health data</li>
      <li>Predicts potential health risks</li>
      <li>Provides preventive recommendations</li>
      <li>Endpoint: <code>GET /risk/{campaignId}</code></li>
      <li>Output: CampaignRiskDTO</li>
    </ul>
  </li>
</ul>

<hr/>

<!-- ================= Turki ================= -->
<h3>Turki’s Contributions</h3>

<ul>
  <li>Designed and wrote the email notification content and HTML layout:
    <ul>
      <li>Custom HTML email templates</li>
      <li>Clear Arabic notification messages</li>
      <li>Consistent formatting and styling</li>
    </ul>
  </li>

  <li>Implemented the WhatsApp notification service:
    <ul>
      <li>Dedicated WhatsApp service</li>
      <li>Real-time system notifications</li>
      <li>Clear and readable message formatting</li>
    </ul>
  </li>

  <li>Added and managed DTOs for the Objection workflow:
    <ul>
      <li>Objection submission DTO (reason only)</li>
      <li>Objection approval and rejection DTOs</li>
      <li>Reporting DTOs</li>
    </ul>
  </li>

  <li>Designed the project logo and visual identity.</li>
</ul>

<hr/>

<h2>Core Endpoints Implemented by Turki</h2>

<h3>Admin Endpoints</h3>
<table border="1" cellpadding="8">
  <tr>
    <th>Method</th>
    <th>Endpoint</th>
    <th>Description</th>
  </tr>
  <tr><td>PUT</td><td>/activate-kitchen/{id}</td><td>Activate kitchen</td></tr>
  <tr><td>PUT</td><td>/suspend-kitchen/{id}</td><td>Suspend kitchen</td></tr>
  <tr><td>PUT</td><td>/assign-kitchens/{inspectorId}</td><td>Assign kitchens to inspector</td></tr>
  <tr><td>PUT</td><td>/reject-kitchen/{kitchenId}</td><td>Reject kitchen</td></tr>
</table>

<h3>Reports & AI Endpoints</h3>
<table border="1" cellpadding="8">
  <tr>
    <th>Method</th>
    <th>Endpoint</th>
    <th>Description</th>
  </tr>
  <tr><td>GET</td><td>/approval-report</td><td>Objection approval statistics report</td></tr>
  <tr><td>POST</td><td>/ai/meal/validate-for-pilgrim</td><td>AI-based meal validation using pilgrim health record</td></tr>
  <tr><td>POST</td><td>/ai/inspectors/evaluate-performance/{inspectorId}</td><td>AI-based inspector performance evaluation</td></tr>
</table>

<h3>Campaign & Health Endpoints</h3>
<table border="1" cellpadding="8">
  <tr>
    <th>Method</th>
    <th>Endpoint</th>
    <th>Description</th>
  </tr>
  <tr><td>PUT</td><td>/add-pilgrim/{pilgrimId}/{campaignId}</td><td>Add pilgrim to campaign</td></tr>
  <tr><td>PUT</td><td>/close-registration/{campaignId}</td><td>Close campaign registration</td></tr>
  <tr><td>GET</td><td>/classify-risk/{pilgrimId}</td><td>Classify pilgrim health risk</td></tr>
</table>
