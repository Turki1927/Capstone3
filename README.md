<h1>Project Responsibilities</h1>

<hr/>

<h2>Muath’s Contributions to the Project</h2>

<ul>
  <li>Designed and implemented core domain models and database structure, including:
    <ul>
      <li>Pilgrim</li>
      <li>Campaign</li>
      <li>Inspector</li>
      <li>Violation</li>
      <li>Objection</li>
    </ul>
  </li>

  <li>Designing the complete Database Diagram</li>

  <li>Implemented full business logic for violations and objections, including:
    <ul>
      <li>Linking violations to inspectors and kitchens</li>
      <li>Allowing only one objection per violation</li>
      <li>Approving and rejecting objections with proper status handling</li>
      <li>Automatically closing violations when objections are approved</li>
    </ul>
  </li>

  <li>Developed violation enforcement rules, including:
    <ul>
      <li>Automatically suspending a kitchen when violations exceed a defined threshold</li>
      <li>Managing violation lifecycle (open, close, reopen)</li>
    </ul>
  </li>

  <li>Built and integrated the emergency reporting feature using n8n, including:
    <ul>
      <li>Designing the n8n workflow for emergency cases</li>
      <li>Creating a dedicated backend service to communicate with n8n</li>
      <li>Sending real-time emergency campaign reports to admins via a Telegram bot</li>
      <li>Broadcasting emergency messages to all admins using their stored Telegram chat IDs</li>
    </ul>
  </li>

  <li>Implemented admin Telegram integration, including:
    <ul>
      <li>Storing Telegram chatId in the Admin model</li>
      <li>Providing an endpoint to retrieve all admin chat IDs for notifications</li>
      <li>Linking backend data with n8n Telegram nodes dynamically</li>
    </ul>
  </li>
</ul>

<hr/>

<h2>Rawan’s Contributions to the Project</h2>

<h3>Models, Services and Controllers</h3>

<ul>
  <li><strong>Kitchen</strong>
    <ul>
      <li>Provides full CRUD operations</li>
      <li>Includes additional endpoints</li>
      <li>Generates summarized kitchen reports</li>
      <li>Output: KitchenReportDTO</li>
    </ul>
  </li>

  <li><strong>Meal</strong>
    <ul>
      <li>Provides full CRUD operations</li>
      <li>Includes additional endpoints</li>
    </ul>
  </li>

  <li><strong>Rating</strong>
    <ul>
      <li>Handles kitchen evaluations</li>
      <li>Provides full CRUD operations</li>
    </ul>
  </li>
</ul>

<h3>Services</h3>
<ul>
  <li><strong>Email Service</strong>
    <ul>
      <li>Sends automated email notifications</li>
      <li>Notifies the pilgrim when they are successfully registered in a campaign</li>
      <li>Includes registration confirmation details such as campaign information</li>
    </ul>
  </li>
</ul>

<h3>AI</h3>
<ul>
  <li><strong>Multi-Language Health Advice</strong>
    <ul>
      <li>Provides AI-generated health and nutrition advice</li>
      <li>Supports multiple languages</li>
      <li>Endpoint: GET /advice/{lang}</li>
      <li>Output: PilgrimAdviceDTO</li>
    </ul>
  </li>

  <li><strong>Campaign Health Risk Prediction</strong>
    <ul>
      <li>Analyzes campaign-level health data</li>
      <li>Predicts potential health risks</li>
      <li>Provides preventive recommendations</li>
      <li>Endpoint: GET /risk/{campaignId}</li>
      <li>Output: CampaignRiskDTO</li>
    </ul>
  </li>
</ul>

<hr/>

<h2>Turki’s Contributions to the Project</h2>

<ul>
  <li>Designed and wrote the email notification content and HTML layout, including:
    <ul>
      <li>Custom HTML email templates</li>
      <li>Clear Arabic notification messages</li>
      <li>Proper formatting and styling for system emails</li>
    </ul>
  </li>

  <li>Implemented the WhatsApp notification service, including:
    <ul>
      <li>Creating a dedicated WhatsApp service</li>
      <li>Sending real-time notifications for system events</li>
      <li>Structuring clear and readable WhatsApp messages</li>
    </ul>
  </li>

  <li>Added and managed DTOs for the Objection workflow, including:
    <ul>
      <li>DTO for submitting an objection (reason only)</li>
      <li>DTO for approving and rejecting objections</li>
      <li>DTOs for objection responses and reports</li>
      <li>Ensuring clean request/response separation using DTOs</li>
    </ul>
  </li>

  <li>Designed the project logo and visual identity, including:
    <ul>
      <li>Creating the logo concept</li>
      <li>Aligning branding with the project theme</li>
      <li>Using the logo consistently in the presentation</li>
    </ul>
  </li>
</ul>

<hr/>

<h2>Turki’s Core Endpoints</h2>

<h3>Admin Endpoints</h3>
<table border="1" cellpadding="8">
  <tr>
    <th>Method</th>
    <th>Endpoint</th>
  </tr>
  <tr><td>PUT</td><td>/activate-kitchen/{id}</td></tr>
  <tr><td>PUT</td><td>/suspend-kitchen/{id}</td></tr>
  <tr><td>PUT</td><td>/assign-kitchens/{inspectorId}</td></tr>
  <tr><td>PUT</td><td>/reject-kitchen/{kitchenId}</td></tr>
</table>

<h3>Reports & AI Endpoints</h3>
<table border="1" cellpadding="8">
  <tr>
    <th>Method</th>
    <th>Endpoint</th>
  </tr>
  <tr><td>GET</td><td>/approval-report</td></tr>
  <tr><td>POST</td><td>/ai/meal/validate-for-pilgrim</td></tr>
  <tr><td>POST</td><td>/ai/inspectors/evaluate-performance/{inspectorId}</td></tr>
</table>

<h3>Campaign & Health Endpoints</h3>
<table border="1" cellpadding="8">
  <tr>
    <th>Method</th>
    <th>Endpoint</th>
  </tr>
  <tr><td>PUT</td><td>/add-pilgrim/{pilgrimId}/{campaignId}</td></tr>
  <tr><td>PUT</td><td>/close-registration/{campaignId}</td></tr>
  <tr><td>GET</td><td>/classify-risk/{pilgrimId}</td></tr>
</table>
